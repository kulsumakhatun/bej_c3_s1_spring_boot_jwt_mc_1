package com.niit.service;

import com.niit.domain.Artist;
import com.niit.domain.Track;
import com.niit.exception.TrackAlreadyExist;
import com.niit.exception.TrackNotFoundException;
import com.niit.repository.TrackRepository;
import com.niit.services.TrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrcakServiceTest {
@Mock
    TrackRepository trackRepository;

@InjectMocks
    TrackServiceImpl trackService;

private Track track;
private Artist artist;

List <Track> trackList;


@BeforeEach
    public void setUp(){
    this.artist = new Artist(101,"A.R.Rahman");
    this.track = new Track(100,"Barso re",5,this.artist);
    trackList = new ArrayList<>();
    trackList.add(track);
    this.artist = new Artist(103,"Atif");
    this.track = new Track(103,"Barso re",5,this.artist);
    trackList.add(track);

}


@Test
public void saveTrackSuccess() throws TrackAlreadyExist {
    when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(null));
    when(trackRepository.save(track)).thenReturn(track);
    assertEquals(track,trackService.saveTrack(track));

}

    @Test
    public void saveTrackFailure(){
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(track));
        assertThrows(TrackAlreadyExist.class,()->trackService.saveTrack(track));
    }

    @Test
    public void getAllTrackSuccess() throws Exception {
    when(trackRepository.findAll()).thenReturn(trackList);
    assertEquals(trackList.size(),trackService.getAllTrack().size());
    }

    @Test
    public void getAllTrackFailure(){
        List<Track> trackList1 =new ArrayList<>();
        when(trackRepository.findAll()).thenReturn(trackList1);
        assertThrows(TrackNotFoundException.class,()->trackService.getAllTrack());
    }

    @Test
    public void deleteTrackByIdSuccess() throws TrackNotFoundException {
    when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(track));
    String expected = trackService.deleteTrack(track.getTrackId());
    assertEquals(expected,"Tracked deleted");
    }

    @Test
    public void deleteTrackByIdFailure() {
        when(trackRepository.findById(track.getTrackId())).thenReturn(Optional.ofNullable(null));
        assertThrows(TrackNotFoundException.class,()->trackService.deleteTrack(track.getTrackId()));
    }

    @Test
    public void getAllTrackByRatingSuccess() throws TrackNotFoundException {
    when(trackRepository.findALlTrackByRating()).thenReturn(trackList);
    assertEquals(trackList.size(),trackService.getAllTrackByTrackRating().size());
    }

    @Test
    public void getAllTrackByRatingFailure() throws TrackNotFoundException {
        List<Track> trackList1 =new ArrayList<>();
        when(trackRepository.findALlTrackByRating()).thenReturn(trackList1);
        assertThrows(TrackNotFoundException.class,()->trackService.getAllTrackByTrackRating());
    }

    @Test
    public void getAllTrackByArtistNameSuccess() throws TrackNotFoundException {
    when(trackRepository.findAllTrackByTrackArtist("Atif")).thenReturn(trackList);
    assertEquals(trackList.size(),trackService.getAllTrackByArtistName("Atif").size());
    }

    @Test
    public void getAllTrackByArtistNameFailure() throws TrackNotFoundException {
        List<Track> trackList1 = new ArrayList<>();
        when(trackRepository.findAllTrackByTrackArtist("Atif")).thenReturn(trackList1);
        assertThrows(TrackNotFoundException.class,()->trackService.getAllTrackByArtistName("Atif").size());
    }




@AfterEach
    public void tearDown(){
    this.artist =null;
    this.track =null;
}





}
