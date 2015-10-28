package PokerClient;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



@Lazy
@SpringBootApplication
public class ClientApp extends Application {

    private static ConfigurableApplicationContext applicationContext;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	stage.setScene(getSceneFromFXML("MainWindow"));
        stage.show();
    }

    @Override
    public void init() throws Exception {
        SpringApplication app = new SpringApplication(ClientApp.class);
        app.setWebEnvironment(false);
        applicationContext = app.run();
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

	public static Scene getSceneFromFXML(String fxmlName) throws IOException {
		Parent root = (Parent) load(fxmlName);
		Scene scene = new Scene(root);
		return scene;
	}

	public static Object load(String name) {

		try (InputStream fxmlStream = ClientApp.class.getResource("fxml/" + name + ".fxml").openStream()) {
			FXMLLoader loader = new FXMLLoader();
			loader.setControllerFactory(applicationContext::getBean);
			return loader.load(fxmlStream);
		} catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}
    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

}
