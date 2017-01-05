package org.uk.cyberbyte.armaworks.Api;

import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiPlug {

    @GET("test.html")
    Call<List<Player>> getPlayers();

    @GET("armalife/player/{id}")
    Call<Player> getPlayer(@Path("id") int playerId);

    @POST("armalife/player")
    Call<Player> createPlayer(@Body Player player);

    @PUT("armalife/player/{id}")
    Call<Player> updatePlayer(@Path("id") int playerId, @Body Player player);

}
