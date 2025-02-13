package net.nordeck.ovc_minimal_backend.controllers;

import lombok.extern.slf4j.Slf4j;
import net.nordeck.ovc_minimal_backend.dtos.JwtDTO;
import net.nordeck.ovc_minimal_backend.services.JWTVideoTestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
@Slf4j
public class JwtController {

    private final JWTVideoTestService jwtVideoTestService;

    @Value("${room.prefix}")
    private String roomPrefix;

    public JwtController(JWTVideoTestService jwtVideoTestService) {
        this.jwtVideoTestService = jwtVideoTestService;
    }


    @GetMapping("/jwt")
    public ResponseEntity<JwtDTO> getJwtForRoom(@RequestParam("roomName") String room) {
        if (room == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if (room.length() > 50 || room.length() < 2)  throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (!room.startsWith(roomPrefix)) {
            log.warn("not allowed to generate videotest token for room [{}]",  room.trim());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        log.debug("generated videotest token for room [{}]", room);

        JwtDTO j = new JwtDTO(jwtVideoTestService.makeJWT(room));
        return new ResponseEntity<>(j, HttpStatus.OK);
    }



}
