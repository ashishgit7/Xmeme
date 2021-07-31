package com.crio.starter.service;

// package com.crio.starter.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crio.starter.data.Meme;
import com.crio.starter.repository.MemeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MemeService {
    @Autowired
    private MemeRepository memeRepository;

    // return 100 most recent memes

    public List<Meme> getTopMemes() {

        List<Meme> memeList = memeRepository.findAll();
        int sz = memeList.size();
        Collections.reverse(memeList);
        // memeRepository.deleteAll();
        return memeList.stream().filter(m -> m.getId() >= sz - 100).collect(Collectors.toList());
    }
    public void deleteAll(){
        memeRepository.deleteAll();
    }

    // saves the meme into the database

    public long createMeme(Meme meme) throws Exception {
        List<Meme> allMeme = this.memeRepository.findAll();
        long id = allMeme.size();
        
        for (Meme currMeme : allMeme) {
            
            if (currMeme.getName().equals(meme.getName())&&
            currMeme.getCaption().equals(meme.getCaption())
            &&currMeme.getUrl().equals(meme.getUrl())) {
                id =  currMeme.getId(); 
                System.out.println("hello my id is");
                throw new Exception("message");
              
            }
        }
        meme.setId(id);
        this.memeRepository.save(meme);
        return(id);
        }
        
    
    public Meme getMemeById(long id) {
        Meme memeOptional = memeRepository.findById(id);
        return memeOptional;
    }

    
    //returns the meme which has the given id
  
    // public Meme getMemeById(long id) {
    //     Optional<Meme> memeOptional = memeRepository.findById(id);
    //     if(memeOptional.isPresent()){
    //         return memeOptional.get();
    //     }
    //     else{
    //         return null;
    //     }
    // }

    // //updates the meme
   
    // public void updateMeme(String caption, String url, long id) {
    //     memeRepository.updateMeme(caption,url,id);
    // }
}
