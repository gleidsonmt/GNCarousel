
# GNCarousel
### The carousel is a control of the overlapping layer, which navigates about her, showing one for once.

<div text-align="center"><img src="src/view.gif" align="middle" /></div>


![skeleton](src/model.jpg)


```java
  GNCarousel carousel = new GNCarousel();
  carousel.setTitle("Carousel");
  carousel.setSubtitle("This is a subtitle.");
  carousel.setVelocity(Duration.millis(500D));
  carousel.getItems().setAll(createItems());
```
