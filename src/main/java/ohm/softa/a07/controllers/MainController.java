package ohm.softa.a07.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ohm.softa.a07.api.OpenMensaAPI;
import ohm.softa.a07.model.Meal;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private ListView<String> mealsList;

	private OpenMensaAPI api = new Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl("https://openmensa.org/api/v2/")
		.build()
		.create(OpenMensaAPI.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the event handler (callback)
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// create a new (observable) list and tie it to the view
				ObservableList<String> list = FXCollections
					.observableArrayList(getMeals());
				mealsList.setItems(list);
			}
		});
	}

	private List<String> getMeals() {
		List<Meal> response = null;
		try {
			response = api.getMealsByDate(
					229, LocalDate.now().toString())
				.execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (response == null) {
			return Collections.emptyList();
		}

		return response.stream()
			.map(Meal::toString)
			.collect(Collectors.toList());
	}
}
