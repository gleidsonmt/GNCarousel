
<h1></h1>

<p align="center">
  <img src="src/logo.png"  />
</p>

<h1></h1>

<h5 align="center"> This project is part of the set of custom components created for JavaFx </h5>

<h1>

<h5 align="center"> 
  The carousel is a control of the overlapping layer, which navigates about her, showing one for once.
</h5>
</h1>

```java
  GNCarousel carousel = new GNCarousel();
  carousel.setTitle("Carousel");
  carousel.setSubtitle("This is a subtitle.");
  carousel.setVelocity(Duration.millis(500D));
  carousel.getItems().setAll(createItems());
```
