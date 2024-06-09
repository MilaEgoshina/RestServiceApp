package com.example.app.servlet;

import com.example.app.dto.IncomingWorkerDTO;
import com.example.app.dto.OutgoingFullWorkerDTO;
import com.example.app.dto.UpdateWorkerDTO;
import com.example.app.exception.NotFoundException;
import com.example.app.service.WorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/worker/*"})
public class WorkerServlet extends HttpServlet {

    private final WorkerService workerService;
    private final ObjectMapper objectMapper;

    public WorkerServlet() {
        this.workerService = new WorkerService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        setHeaderResp(resp);
        String response = "";
        String[] parts = req.getPathInfo().split("/");
        if("all".equals(parts[1])){
            List<OutgoingFullWorkerDTO> outgoingFullWorkerDTOS = workerService.findAllWorker();
            resp.setStatus(HttpServletResponse.SC_OK);
            response = objectMapper.writeValueAsString(outgoingFullWorkerDTOS);
        }else {
            Long workerId = Long.parseLong(parts[1]);
            try {
                OutgoingFullWorkerDTO outgoingFullWorkerDTO = workerService.findWorkerById(workerId);
                resp.setStatus(HttpServletResponse.SC_OK);
                response = objectMapper.writeValueAsString(outgoingFullWorkerDTO);
            } catch (NotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response = e.getMessage();
            }
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaderResp(resp);
        String jsonReq = getJsonReq(req);

        String response = "";

        Optional<IncomingWorkerDTO> incomingWorkerDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, IncomingWorkerDTO.class));
        IncomingWorkerDTO workerDTO = incomingWorkerDTO.orElseThrow(IllegalArgumentException::new);
        response = objectMapper.writeValueAsString(workerService.saveWorker(workerDTO));

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaderResp(resp);
        String jsonReq = getJsonReq(req);

        String response = "";

        Optional<UpdateWorkerDTO> updateWorkerDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, UpdateWorkerDTO.class));
        UpdateWorkerDTO workerDTO = updateWorkerDTO.orElseThrow(IllegalArgumentException::new);
        try {
            workerService.updateWorker(workerDTO);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response = e.getMessage();
        }

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setHeaderResp(resp);
        String response = "";

        try {

            String[] parts = req.getPathInfo().split("/");
            Long workerId = Long.parseLong(parts[1]);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            workerService.deleteWorkerById(workerId);
        }catch (Exception exception){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response = "Bad request";
        }

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }
    private void setHeaderResp(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private String getJsonReq(HttpServletRequest req) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = req.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

}
