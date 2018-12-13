package org.deepviss.deepvissserver.rest;

import org.deepviss.deepvissserver.mocking.FrameMocker;
import org.deepviss.deepvissserver.model.DeepVISSFrame;
import org.deepviss.deepvissserver.model.DeepVISSProcessingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/rest/examples/")
@ApiIgnore
public class MockController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private FrameMocker frameMocker;
    @PostConstruct
    private void initialize() throws IOException {
        frameMocker =new FrameMocker();
    }

    @CrossOrigin(origins = "*")
    //@RequestMapping(value = "/frames", method = RequestMethod.GET, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(value = "/frames", method = RequestMethod.GET, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    List<DeepVISSFrame> getFrames(
            //@RequestParam(value = "masterKey", required = true) String masterKey
    ) throws IOException, InterruptedException {
        return frameMocker.getMockedFrames();
    }


    @CrossOrigin(origins = "*")
    //@RequestMapping(value = "/frames", method = RequestMethod.GET, produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @RequestMapping(value = "/processingRequest", method = RequestMethod.POST, produces= {MediaType.APPLICATION_JSON_VALUE})
    DeepVISSProcessingRequest getProcessingRequest(
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) throws IOException, InterruptedException {
        return frameMocker.getMockedProcessingRequest(picture.getBytes(), picture.getContentType());
    }

}