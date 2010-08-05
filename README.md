nxj-chat
========

A simple and customizable bluetooth protocol for leJOS.

Features
--------

* Provides an object-oriented abstraction to leJOS bluetooth interface.
* Logically group data in Requests
* Easily customizable for custom needs
* Emailish request header with name, subject, module, sender...
* Answer to one individual received request
* Send follow-ups to sent requests
* Block thread execution until reply is received

Motivation
----------
I was working on a project for school with the Lego Mindstorms NXT.
Because the proposed language, NXC, didn't appeal to me and I always wanted to
learn a little Java, I decided to go with leJOS.

As we were planning our projects, the idea arose that we wanted to have
robots that communicate with each other and send data to the computer.
During our research on the web we discovered, that an NXT could not have more
than one outgoing Bluetooth connection at a time. To compensate for this
communication problem, we wanted our Computer to act as master between the NXTs.

I decided to abstract the NXTs raw bluetooth communication and introduce an OO 
approach. Soon, nxj-chat was born.

Future
------

Because the project is finished, I have no longer access to the NXTs. Therefore,
I am unable to extend this project any further because I lack the possibility to
test code. However, feel free to open an issue on github and I will look into it.

That's why I have decided to publish the source on github, so that other people
can use or fork it and add their own features.

Unfortunately I haven't been very busy documenting the source code. But I hope
you can figure out how it works by looking at the sample application I have
provided.

Bottom Line
-----------
As I have mentioned, this was my first Java Experience and please excuse the
complicated class hierachies...