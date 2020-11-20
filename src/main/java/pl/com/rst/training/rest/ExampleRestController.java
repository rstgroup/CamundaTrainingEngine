package pl.com.rst.training.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.ws.rs.QueryParam;

@RestController()
@Slf4j
public class ExampleRestController {

    public static final String REST_API_PREFIX = "/rest/v1/example";

    private int invokeCounter = 0;
    private String raceConditionVulnearableResource;

    @GetMapping(value = REST_API_PREFIX + "/serviceA")
    public Integer serviceA(
            @QueryParam("shouldFail") boolean shouldFail) {
        log.info("Service A invoked");

        if (shouldFail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return ++invokeCounter;
    }

    @GetMapping(value = REST_API_PREFIX + "/serviceB")
    public String serviceB(
            @QueryParam("shouldFail") boolean shouldFail) {
        log.info("Service B invoked");

        if (shouldFail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "Service B response";
    }

    @GetMapping(value = REST_API_PREFIX + "/serviceC")
    public String serviceC(
            @QueryParam("shouldFail") boolean shouldFail) {
        log.info("Service C invoked");

        if (shouldFail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "Service C response";
    }

    @GetMapping(value = REST_API_PREFIX + "/serviceD")
    public String serviceD(
            @QueryParam("shouldFail") boolean shouldFail) throws InterruptedException {
        log.info("Service D start processing");
        raceConditionVulnearableResource = "Service D response";
        Thread.sleep(4000);
        log.info("Service D stop processing");
        if (shouldFail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return raceConditionVulnearableResource;
    }

    @GetMapping(value = REST_API_PREFIX + "/serviceE")
    public String serviceE(
            @QueryParam("shouldFail") boolean shouldFail) throws InterruptedException {
        log.info("Service E start processing");
        raceConditionVulnearableResource = "Service E response";
        Thread.sleep(2000);
        log.info("Service E stop processing");
        if (shouldFail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return raceConditionVulnearableResource;
    }
}
