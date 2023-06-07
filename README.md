
![Version](https://img.shields.io/badge/Version-2.1.4-green.svg?style=for-the-badge)
[![Release](https://img.shields.io/badge/Release-v2.1.0-blue.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/releases/tag/1.0)
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
carousel.setItems(createItems());
carousel.setArrows(true);
carousel.setAutoRide(true);
carousel.setItems(createItems());
```

<h5>Inline css  </h5>

```java
 -gn-auto-ride : true;
 -gn-transition-duration : 300ms;
```
<h1></h1>

<h5>View</h5>

<p align="center"><img src="src/view.gif"/></p>
