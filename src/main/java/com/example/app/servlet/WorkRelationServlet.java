package com.example.app.servlet;

import com.example.app.dto.*;
import com.example.app.exception.NotFoundException;
import com.example.app.service.WorkRelationsService;
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

@WebServlet(urlPatterns = {"/workRelations/*"})
public class WorkRelationServlet extends HttpServlet {

    private final WorkRelationsService workRelationsService;
    private final ObjectMapper objectMapper;

    public WorkRelationServlet(){
        this.workRelationsService = new WorkRelationsService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        setHeaderResp(resp);
        String response = "";
        String[] parts = req.getPathInfo().split("/");
        if("all".equals(parts[1])){
            List<OutgoingWorkRelationsDTO> outgoingWorkRelationsDTOS = workRelationsService.findAllWorkRelations();
            resp.setStatus(HttpServletResponse.SC_OK);
            response = objectMapper.writeValueAsString(outgoingWorkRelationsDTOS);
        }else {
            Long id = Long.parseLong(parts[1]);
            try {
                OutgoingWorkRelationsDTO workRelationsDTO = workRelationsService.findWorkRelationsById(id);
                resp.setStatus(HttpServletResponse.SC_OK);
                response = objectMapper.writeValueAsString(workRelationsDTO);
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

        Optional<IncomingWorkRelationsDTO> incomingWorkRelationsDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, IncomingWorkRelationsDTO.class));
        IncomingWorkRelationsDTO workRelationsDTO = incomingWorkRelationsDTO.orElseThrow(IllegalArgumentException::new);
        response = objectMapper.writeValueAsString(workRelationsService.saveWorkRelations(workRelationsDTO));

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaderResp(resp);
        String jsonReq = getJsonReq(req);

        String response = "";

        try {
            if (req.getPathInfo().contains("/addWorker/")) {
                String[] parts = req.getPathInfo().split("/");
                if (parts.length > 3 && "addUser".equals(parts[2])) {
                    Long id = Long.parseLong(parts[1]);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    Long workerId = Long.parseLong(parts[3]);
                    workRelationsService.addWorkerToRelations(id, workerId);
                }
            }else {

                Optional<UpdateWorkRelationsDTO> updateWorkRelationsDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, UpdateWorkRelationsDTO.class));
                UpdateWorkRelationsDTO workRelationsDTO = updateWorkRelationsDTO.orElseThrow(IllegalArgumentException::new);
                workRelationsService.updateWorkRelations(workRelationsDTO);
            }
        }catch (NotFoundException e){

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
            workRelationsService.deleteWorkRelationsById(workerId);
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

