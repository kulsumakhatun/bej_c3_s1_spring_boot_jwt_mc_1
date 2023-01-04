package com.niit.controller;

import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyExist;
import com.niit.exception.TrackNotFoundException;
import com.niit.services.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/track/api")
public class TrackController {

   ITrackService iTrackService;

   public TrackController(ITrackService iTrackService){
       this.iTrackService =iTrackService;
   }

   @PostMapping("/track")
    public ResponseEntity<?> saveTracks(@RequestBody Track track) throws TrackAlreadyExist {
       return  new ResponseEntity<>(iTrackService.saveTrack(track),HttpStatus.CREATED);
   }

   @GetMapping("/track")
    public  ResponseEntity<?> getAllTracks() throws TrackNotFoundException {
       return  new ResponseEntity<>(iTrackService.getAllTrack(),HttpStatus.OK);
   }

   @DeleteMapping("/track/{Id}")
    public ResponseEntity<?> deleteTrackById(@PathVariable int Id) throws TrackNotFoundException{
      return new ResponseEntity<>(iTrackService.deleteTrack(Id),HttpStatus.OK);
   }
   @GetMapping("/track1")
    public ResponseEntity<?> getTrackByTrackRating() throws TrackNotFoundException{
       return new ResponseEntity<>(iTrackService.getAllTrackByTrackRating(),HttpStatus.OK);
   }

   @GetMapping("/track/{name}")
    public ResponseEntity<?> getTrackByTrackArtist(@PathVariable String name) throws TrackNotFoundException{
       return  new ResponseEntity<>(iTrackService.getAllTrackByArtistName(name),HttpStatus.OK);
   }

}
