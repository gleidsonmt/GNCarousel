
![Version](https://img.shields.io/badge/Version-1.1.0-green.svg?style=for-the-badge)
[![Release](https://img.shields.io/badge/Release-v1.0--alpha-blue.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/releases/tag/1.0)
[![License](https://img.shields.io/github/license/Gleidson28/GNCarousel.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/blob/master/LICENSE) 


<h1></h1>

<p align="center">
  <img src="src/logo.png"  />
</p>

<h1></h1>
<h6 align="center"> This project is part of the set of custom components created for JavaFx. </h6>

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

<h1></h1>

<h5>View</h5>

<p align="center"><img src="src/view.gif"/></p>
