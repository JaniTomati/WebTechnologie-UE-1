README FEED-READER-HTTP-API.
DATE.  June 8th, 2017.



OPTIONAL: EIGENE DATEIEN AUS VORANGEGANGENEN ÜBUNGEN BENUTZEN.

  - Ersetzen Sie die css/feedreader.css durch Ihr Stylesheet.
  - Ersetzen Sie die xsl/atom-to-html.xsl durch Ihre XSLT-Datei aus der letzten Übung. Der Pfad zur CSS-Datei sollte css/feedreader.css oder auf Ihr Stylesheet angepasst sein.
  - Ersetzen Sie die WEB-INF/classes/atom.xsd durch Ihr Schema.
  - Ersetzen Sie die FeedModel.java aus der letzten Übung. Am Anfang der mitgelieferten FeedModel.java befinden sich einige Änderungen zur ursprünglichen Datei: Die File-Variables für das Instanzdokument und Schema werden über getResource geladen, so dass sie auch im Servlet zur Verfügung stehen (dazu müssen beide in WEB-INF/classes liegen) und eine neue Methode getFeed() erlaubt den direkten Zugriff auf den Feed (wird in dieser Aufgabe benötigt). Übertragen Sie die Änderungen entsprechend in Ihre Datei.



DEPLOYMENT.

Erstellen Sie wieder zuerst die JAXB Klassen:
    xjc -p jaxb WEB-INF/classes/atom.xsd
    javac jaxb/package-info.java

Zum kompilieren des Servlets (hier FeedReaderServlet.java) müssen Sie die lib/javax.servlet-3.0.jar Bibliothek mit angeben:
    javac -classpath .:lib/javax.servlet-3.0.jar FeedReaderServlet.java

Für das Deployment müssen alle .class-Dateien im WEB-INF/classes Ordner liegen (als Servlet-Name wird hier FeedReaderServlet angenommen):
    WEB-INF/classes/
                atom.xsd
                feed.xml
                FeedModel.class
                FeedReaderServlet.class
                jaxb/
                    Link.class
                    Entry.class
                    Person.class
                    Feed.class
                    package-info.class

<---- soweit sind wir: für die b) Toolslide letzte Folie

Das erstellte WAR muss neben WEB-INF auch die statischen Ressourcen (add-entry.html sowie die Ordner css, img und xsl) auf oberster Ebene enthalten. Tomcat stellt diese Ressourcen dann unter localhost:8080/feed-reader zur Verfügung. Unter Linux kann das WAR mit folgendem Aufruf erzeugt werden:
    zip -r feed-reader.war add-entry.html css img WEB-INF xsl

Hinweis: der Dateiname des Archivs muss feed-reader.war sein!



ABGABE.

Geben Sie die gesamte Ordnerstruktur inklusive der neuen Servlet-Datei ab.

