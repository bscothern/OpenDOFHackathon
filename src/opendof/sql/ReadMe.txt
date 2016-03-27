ReadMe.Txt for opendof.sql

Contributors: Aaron Hsu, Bharath Gunasekaran, Braden Scothern, and Charles Khong

DOFdb uses the OpenDOF SDK to accomplish the distribution of the data over the network.
We created a small application that bridges OpenDOF calls with MySQL Language that
accesses a MySQL database. By doing this we were able to call the functionality of SQL 
without even knowing how SQL works. This proof of concept reveals that openDOF's
provider object can easily be created into a stand alone Database bridge. 
This means that openDOF easily be used to bridge with any other Data access API. 
We extended this proof of concept by attempting to connect a DOF bridge with 
the Twitter API to access the data on your twitter account. 