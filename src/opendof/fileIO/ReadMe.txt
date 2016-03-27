ReadMe.Txt for opendof.fileIO

Contributors: Aaron Hsu, Bharath Gunasekaran, Braden Scothern, and Charles Khong

We created DOFWriter and DOFReader objects. Using these objects we were able to
design a pattern through these objects for writing and reading a file from anywhere
on a cluster. 

To try the DOFWriter, execute MainWriterProvider (change IP addresses as desired) and then execute MainWriterRequestor (again change IP addresses).
This will append "Hello World" to def.txt

To try the DOFReader, execute MainReaderProvider (change IP addresses as desired) and then execute MainReaderRequestor (IP addresses).
This will read the contents of abc.txt and output them into MainReaderRequestor's console.