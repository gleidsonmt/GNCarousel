
# GNCarousel
### The carousel is a control of the overlapping layer, which navigates about her, showing one for once.

<img src="src/view.gif" align="middle" />


![skeleton](src/model.jpg)


```java
  GNCarousel carousel = new GNCarousel();
  carousel.setTitle("Carousel");
  carousel.setSubtitle("This is a subtitle.");
  carousel.setVelocity(Duration.millis(500D));
  carousel.getItems().setAll(createItems());
```
