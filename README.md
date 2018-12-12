
<h1></h1>

<p align="center">
  <img src="src/logo.png"  />
</p>

<h1></h1>
<h6 align="center"> This project is part of the set of custom components created for JavaFx </h6>

<h1></h1>

<h1> GNCarousel </h1>

<h5 > 
  The carousel is a control of the overlapping layer, which navigates about her, showing one for once.
</h5>

 > First, he creates a skin with one clip and your indices, when navigating between the views one event is shot
 > positioning the next view to side left or right, during the event the next view pushes the actual view to the side
 > left or right.

<h1></h1>

<h5>Basic Usage</h5>

```java
GNCarousel carousel = new GNCarousel();
carousel.setTitle("Carousel");
carousel.setSubtitle("This is a subtitle.");
carousel.setVelocity(Duration.millis(500D));
carousel.getItems().setAll(createItems());
```
