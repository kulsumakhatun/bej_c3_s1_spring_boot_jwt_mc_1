package com.niit.repository;


import com.niit.domain.Artist;
import com.niit.domain.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {


private Track track;
private Artist artist;

@Autowired
 private TrackRepository trackRepository;

@BeforeEach
 public void setUp(){
    this.artist = new Artist(5,"Justin Bieber");
    this.track = new Track(5,"Baby",5,this.artist);
    this.artist = new Artist(6,"Arijit Singh");
    this.track = new Track(6,"Tum hi ho",4,this.artist);
}
@Test
public void onSaveTrackSuccess(){
Track track1 = trackRepository.save(track);
Track track2 = trackRepository.findById(track.getTrackId()).get();
assertEquals(track1,track2);
}

@Test
public void getAllTracksSuccess(){
    trackRepository.deleteAll();
trackRepository.insert(track);
this.artist = new Artist(4,"justin bieber");
this.track =new Track(4,"baby2",4,this.artist);
trackRepository.save(track);
List<Track> list = trackRepository.findAll();
assertEquals(2,list.size());
}

@Test
public void deleteTrackById(){
trackRepository.save(track);
Track track1 = trackRepository.findById(track.getTrackId()).get();
trackRepository.delete(track1);
assertEquals(Optional.empty(),trackRepository.findById(track.getTrackId()));
}

@Test
public void getTrackByTrackRating(){
    trackRepository.save(track);
    List<Track>track1 = trackRepository.findALlTrackByRating();
    assertEquals(1,track1.size());
}

@Test
public void getTrackByArtistName(){
    trackRepository.save(track);
    List<Track> trackList =trackRepository.findAllTrackByTrackArtist("Arijit Singh");
    assertEquals(1,trackList.size());
}




@AfterEach
 public void tearUp(){
    this.track = null;
    this.artist =null;
}

}
