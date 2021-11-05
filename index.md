---
layout: default
Title: Welcome to Index Page
---
## Posts

<ul class="posts">

	  {% for post in site.posts %}
	    <li><span>{{ post.date | date_to_string }}</span> » <a href="/cs5520project{{ post.url }}" title="{{ post.title }}">{{ post.title }}</a></li>
	  {% endfor %}
</ul>
```
_hello, and this is my index page._
<img src = "https://raw.githubusercontent.com/saiqi1999/cs5520project/gh-pages/images/nyan-cat.gif" width="400"/>
```
