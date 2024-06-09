package com.example.app.servlet;

import com.example.app.dto.*;
import com.example.app.exception.NotFoundException;
import com.example.app.service.RoleService;
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

@WebServlet(urlPatterns = {"/role/*"})
public class RoleServlet extends HttpServlet {

    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    public RoleServlet(){
        this.roleService = new RoleService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        setHeaderResp(resp);
        String response = "";
        String[] parts = req.getPathInfo().split("/");
        if("all".equals(parts[1])){
            List<OutgoingRoleDTO> outgoingRoleDTOS = roleService.findAllRole();
            resp.setStatus(HttpServletResponse.SC_OK);
            response = objectMapper.writeValueAsString(outgoingRoleDTOS);
        }else {
            Long roleId = Long.parseLong(parts[1]);
            try {
                OutgoingRoleDTO outgoingRoleDTO = roleService.findRoleById(roleId);
                resp.setStatus(HttpServletResponse.SC_OK);
                response = objectMapper.writeValueAsString(outgoingRoleDTO);
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

        Optional<IncomingRoleDTO> incomingRoleDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, IncomingRoleDTO.class));
        IncomingRoleDTO roleDTO = incomingRoleDTO.orElseThrow(IllegalArgumentException::new);
        response = objectMapper.writeValueAsString(roleService.saveRole(roleDTO));

        PrintWriter printWriter = resp.getWriter();
        printWriter.write(response);
        printWriter.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        setHeaderResp(resp);
        String jsonReq = getJsonReq(req);

        String response = "";

        Optional<UpdateRoleDTO> updateRoleDTO = Optional.ofNullable(objectMapper.readValue(jsonReq, UpdateRoleDTO.class));
        UpdateRoleDTO roleDTO = updateRoleDTO.orElseThrow(IllegalArgumentException::new);
        try {
            roleService.updateRole(roleDTO);
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
            Long roleId = Long.parseLong(parts[1]);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            roleService.deleteRoleById(roleId);
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
