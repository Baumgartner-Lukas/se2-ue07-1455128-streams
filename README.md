# se2-ue07-1455128-streams

Übung 07: Textanalyse
In dieser Übung wollen wir die Streams von Java 8 für die Analyse eines Textes einsetzen. Der Text soll dabei mit der Methode lines der Klasse Files aus Package java.nio von einer Datei eingelesen werden:
static Stream<String> lines(Path path) throws IOException
Zum Beispiel kann man den Text einer Datei mit Namen "sampletext.txt" wie folgt lesen:
java.nio.Files.lines(java.nio.Paths.get("sampletext.txt"))
Das heißt, die Methode lines liefert einen Stream<String> mit den Zeilen der Datei.
Ausgehend von diesem Stream sollen Textanalysen wie folgt implementiert werden. Jede der Methoden ist durch eine EINZIGE (!) Kette von Stream-Operationen zu lösen!
