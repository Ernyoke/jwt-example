package esz.dev.jwtexample.rest;

import esz.dev.jwtexample.rest.dto.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MyController {

    @RequestMapping("/admin")
    public Response forAdmins() {
        return new Response("Should be seen by admins!");
    }

    @RequestMapping("/user")
    public Response forUsers() {
        return new Response("Should be seen by users!");
    }
}
