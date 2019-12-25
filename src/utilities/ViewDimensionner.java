
package utilities;


import javafx.scene.layout.Region;


public class ViewDimensionner {


public static void bindSizes(Region child,Region parent, double widthPercentage, double heightPercentage){
    
    child.minWidthProperty().bind(parent.widthProperty().multiply(widthPercentage));
    child.maxWidthProperty().bind(parent.widthProperty().multiply(widthPercentage));
    
    child.minHeightProperty().bind(parent.heightProperty().multiply(heightPercentage));
    child.maxHeightProperty().bind(parent.heightProperty().multiply(heightPercentage));
}


public static void bindSizes(Region child,Region parent){

    bindSizes(child,parent,1,1);
}






    
}

