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
```
