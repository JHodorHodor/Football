package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


class PitchField {
    private final Button button;
    private final ImageView imageView;
    private boolean hasBall = false;

    PitchField(Button button, ImageView imageView) {
        this.button = button;
        this.imageView = imageView;
        button.setGraphic(imageView);
    }

    void setImages(Image notSelected, Image selected, boolean hasBall){
        imageView.setImage(notSelected);
        button.setOnMouseExited(e -> imageView.setImage(notSelected));
        button.setOnMouseEntered(e -> imageView.setImage(selected));
        this.hasBall = hasBall;
    }

    Button getButton() {
        return button;
    }

    boolean isHasBall() {
        return hasBall;
    }

}
