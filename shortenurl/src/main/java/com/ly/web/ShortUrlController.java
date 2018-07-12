package com.ly.web;

import com.google.common.hash.Hashing;
import com.ly.data.ShortenURL;
import com.ly.exception.BadRequestException;
import com.ly.exception.NotFoundException;
import com.ly.repository.ShortUrlRepository;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.nio.charset.StandardCharsets;

/**
 * Created by yongliu on 7/11/18.
 */

@Api(
  value = "Short URL",
  description = "Short the Long URL and return the short url",
  position = 1
)

@RestController
public class ShortUrlController extends AbstractController{
  
  public static final String SHORT_URL_TPL = "http://localhost:8080/t/";

  private Logger logger = LogManager.getLogger(getClass());

  @Autowired
  private ShortUrlRepository shortUrlRepository;

  @ApiOperation(
    value = "Short the long URL",
    notes = "Short the long URL and return the short the url",
    httpMethod = "POST",
    consumes = "application/json",
    produces = "application/json"
  )
  @ApiResponses({
    @ApiResponse(code = 200, message = "Done", response = ShortenURL.class),
    @ApiResponse(code = 400, message = "URL is required", response = com.ly.data.ApiResponse.class)
  })

  @RequestMapping(value = "/short", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<ShortenURL> shortUrl(@ApiParam(value = "The long url", required = true) @RequestParam("url") String url) throws Exception {

    logger.info("url: {}", url);
    if (url != null && StringUtils.hasText(url)){
      String shortKey = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
      String shortUrl = SHORT_URL_TPL  + shortKey;

      logger.info("short key: {}", shortKey);
      logger.info("short url: {}", shortUrl);

      shortUrlRepository.add(shortKey, url);
      
      return ResponseEntity.ok().body(new ShortenURL(url, shortUrl, shortKey));
    } else {
      throw new BadRequestException(com.ly.data.ApiResponse.ERROR, "URL is required.");
    }
  }
  
  @ApiOperation(
    value = "Decode the Short URL",
    notes = "Decode the Short URL to real URL",
    httpMethod = "GET",
    consumes = "application/json",
    produces = "application/json"
  )
  
  @ApiResponses({
    @ApiResponse(code = 200, message = "Done", response = ShortenURL.class),
    @ApiResponse(code = 405, message = "Not Found the Key", response = com.ly.data.ApiResponse.class)
  })
  
  @RequestMapping(value = "/t/{key}", method = RequestMethod.GET)
  @ResponseBody public ResponseEntity<ShortenURL> decode(
    @ApiParam(name = "key", value = "short url key", required = true)
    @PathVariable("key") String key) throws Exception{
    logger.info("decode the short url......");
    String longUrl = shortUrlRepository.get(key);
    if (longUrl != null){
      
      return ResponseEntity.ok(new ShortenURL(SHORT_URL_TPL+key, longUrl, key));
    } else {
      throw new NotFoundException(com.ly.data.ApiResponse.ERROR, String.format("Not found the key %s", key));
    }
  }


}
