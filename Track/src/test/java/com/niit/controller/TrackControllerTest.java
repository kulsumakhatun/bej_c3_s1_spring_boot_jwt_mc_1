package com.niit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    @Mock
    TrackServiceImpl trackService;
    @InjectMocks
    TrackController trackController;

    Track track;
    Artist artist;
    @Autowired
    MockMvc mockMvc;


    List<Track> trackList;


    @BeforeEach
    public void setUp() {
        this.artist = new Artist(101, "A.R.Rahman");
        this.track = new Track(100, "Barso re", 5, this.artist);
        trackList = new ArrayList<>();
        trackList.add(track);
        this.artist = new Artist(103, "Atif");
        this.track = new Track(103, "Barso re", 5, this.artist);
        trackList.add(track);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @Test
    public void saveTrackSuccess() throws Exception {
        when(trackService.saveTrack(any())).thenReturn(track);
        mockMvc.perform(post("/track/api/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertJsonToString(track)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void saveTrackFailure() throws Exception {
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyExist.class);
        mockMvc.perform(post("/track/api/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllTrackSuccess() throws Exception {
        when(trackService.getAllTrack()).thenReturn(trackList);
        mockMvc.perform(get("/track/api/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllTrackFailure() throws Exception {
        when(trackService.getAllTrack()).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(get("/track/api/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteTrackByIdSuccess() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn("Tracked deleted");
        mockMvc.perform(delete("/track/api/track/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteTrackByIdFailure() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(delete("/track/api/track/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void getAllTrackByRatingSuccess() throws Exception {
        when(trackService.getAllTrackByTrackRating()).thenReturn(trackList);
        mockMvc.perform(get("/track/api/track1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void getAllTrackByRatingFailure() throws Exception {
        when(trackService.getAllTrackByTrackRating()).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(get("/track/api/track1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void getAllTrackByArtistNameSuccess() throws Exception {
     when(trackService.getAllTrackByArtistName(any())).thenReturn(trackList);
        mockMvc.perform(get("/track/api/track/Atif")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void getAllTrackByArtistNameFailure() throws Exception {
        when(trackService.getAllTrackByArtistName(any())).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(get("/track/api/track/Atif")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertJsonToString(track)))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());


    }


    private static String convertJsonToString(final Object ob) {
        String result;
        ObjectMapper mapper=new ObjectMapper();
        try {
            String jsoncontent= mapper.writeValueAsString(ob);
            result=jsoncontent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result="Json parser error";
        }
        return result;
    }



    @AfterEach
    public void tearDown(){
        this.artist =null;
        this.track =null;
    }
}
