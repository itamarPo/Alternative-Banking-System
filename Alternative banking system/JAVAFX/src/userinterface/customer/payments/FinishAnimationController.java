package userinterface.customer.payments;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class FinishAnimationController {

    private ImageView finishedImage;

    public void initialize(){
        ScaleTransition scaleTransition = new ScaleTransition();
        ScaleTransition scale = new ScaleTransition();
        scaleTransition.setNode(finishedImage);
        scaleTransition.setDuration(Duration.millis(2000));
        scaleTransition.setCycleCount(TranslateTransition.INDEFINITE);
        scaleTransition.setInterpolator(Interpolator.LINEAR);
        scaleTransition.setByX(1.3);
        scaleTransition.setByY(1.3);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }
}
