package ohm.softa.a07.api;

import ohm.softa.a07.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Peter Kurfer on 11/19/17.
 */

public interface OpenMensaAPI {
	// example request: GET /canteens/229/days/2017-11-22/meals

	/**
	 * example request: GET /canteens/269/days/2023-05-02/meals
	 * @param canteenId canteenId für Nuremberg is 269
	 * @param date Date in format yyyy-MM-dd (ISO-standard)
	 * @return List of meals for that day
	 */
	@GET ("/canteens/{canteen_id}/days/{date}/meals")
	Call<List<Meal>> getMealsByDate(
		@Path("canteen_id") int canteenId,
		@Path("date") String date);

}
