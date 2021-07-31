package com.crio.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import com.crio.starter.data.Meme;
import com.crio.starter.service.MemeService;

@Controller
public class MemesController {

    @Autowired
    private final MemeService memeService;

    public MemesController(MemeService memeService) {
        this.memeService = memeService;
    }

    // gets the top 100 memes from the database and returns a list of memes
    @GetMapping("/memes")
    public ResponseEntity<List<Meme>> getMemes() {
        List<Meme> result = memeService.getTopMemes();
        System.out.println("hello id");
        return (ResponseEntity.ok(result));
    }

    @PostMapping(value = "/memes")
    public ResponseEntity<String> saveOrUpdateStudent(@RequestBody Meme meme) {
        String id;
        if(meme.getName()==null||meme.getCaption()==null||meme.getUrl()==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "duplicate null ");
        }
        try {
            id = Long.toString(memeService.createMeme(meme));
            System.out.println(id);
        return ResponseEntity.ok("{ \"id\" : "+ id+"}");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "duplicate Entity ");
        }
        
    }
    @GetMapping("/memes/{id}")
    public ResponseEntity<Meme> getMemeFromId(@PathVariable("id") long id ){
        System.out.println("new "+id);
            Meme meme = memeService.getMemeById(id);
            if(meme==null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Id Not Found");
            }
            return(ResponseEntity.ok(meme));
    }
    @GetMapping("delete")
    public ResponseEntity<String> delete(){
        memeService.deleteAll();
        return (ResponseEntity.ok("deleted"));
        
    }

}    