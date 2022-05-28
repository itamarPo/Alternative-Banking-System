package userinterface.customer.payments;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class FinishAnimationController {

    private ImageView finishedImage;

    @FXML
    public void initialize(){
        ScaleTransition scaleTransition = new ScaleTransition();
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(finishedImage);
        scale.setDuration(Duration.millis(2000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(1.3);
        scale.setByY(1.3);
        scale.setAutoReverse(true);
        scale.play();
    }
}
