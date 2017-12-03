package application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AttributeAdder {
	
	public static Snake display(Snake oldSnake) {

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Gear new attribute");

		final ToggleGroup group = new ToggleGroup();
		
		RadioButton radioForeignLang = new RadioButton("Foreign Language:X2");
		radioForeignLang.setToggleGroup(group);
		radioForeignLang.setSelected(true);
		
		RadioButton radioCertificate = new RadioButton("Certificate:X1.5");
		radioCertificate.setToggleGroup(group);
		
		RadioButton radioInternship = new RadioButton("Internship:X3");
		radioInternship.setToggleGroup(group);
		
		class ReturnValue{Snake snake;}
		ReturnValue retVal = new ReturnValue();

		Button btnAdd = new Button("Gear up");
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (radioForeignLang.isSelected()) {
					final Snake newSnake = new ForeignLanguage(oldSnake);
					retVal.snake = newSnake;
				}else if (radioCertificate.isSelected()) {
					final Snake newSnake = new TrainingCertificate(oldSnake);
					retVal.snake = newSnake;
				}else if (radioInternship.isSelected()) {
					final Snake newSnake = new Internship(oldSnake);
					retVal.snake = newSnake;
				}
				window.close();
			}
		});

		VBox layout = new VBox(25);
		layout.getChildren().addAll(radioForeignLang, radioCertificate, radioInternship, btnAdd);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		return retVal.snake;
	}

}
