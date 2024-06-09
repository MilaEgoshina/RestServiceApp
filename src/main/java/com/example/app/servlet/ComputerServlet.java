package com.example.app.servlet;

import com.example.app.dto.*;
import com.example.app.exception.NotFoundException;
import com.example.app.service.ComputerService;
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

@WebServlet(urlPatterns = {"/computer/*"})
public class ComputerServlet extends HttpServlet {

    private final ComputerService computerService;
    private final ObjectMapper objectMapper;

    public ComputerServlet() {

        this.computerService = new ComputerService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        setHeaderResp(resp);
        String response = "";
        String[] parts = req.getPathInfo().split("/");
        if("all".equals(parts[1])){
            List<OutgoingComputerDTO> outgoingComputerDTOS = computerService.findAllComputer();
            resp.setStatus(HttpServletResponse.SC_OK);
            response = objectMapper.writeValueAsString(outgoingComputerDTOS);
        }else {
            Long computerId = Long.parseLong(parts[1]);
            try {
                OutgoingComputerDTO outgoingComputerDTO = computerService.findComputerById(computerId);
                resp.setStatus(HttpServletResponse.SC_OK);
                response = objectMapper.writeValueAsString(outgoingComputerDTO);
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

        Optional<IncomingComputerDTO> incomingComputerDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, IncomingComputerDTO.class));
        IncomingComputerDTO computerDTO = incomingComputerDTO.orElseThrow(IllegalArgumentException::new);
        response = objectMapper.writeValueAsString(computerService.saveComputer(computerDTO));

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaderResp(resp);
        String jsonReq = getJsonReq(req);

        String response = "";

        Optional<UpdateComputerDTO> updateComputerDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, UpdateComputerDTO.class));
        UpdateComputerDTO computerDTO = updateComputerDTO.orElseThrow(IllegalArgumentException::new);
        try {
            computerService.updateComputer(computerDTO);
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
            Long computerId = Long.parseLong(parts[1]);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            computerService.deleteComputerById(computerId);
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
