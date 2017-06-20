#!/usr/bin/perl
# Jula McGibbon (115167), Jana Puschmann (115753),
# Christopher Marx (114602), Josef Roth (115850)

$query = $ENV{'QUERY_STRING'};
($this_page, $from_page) = split(/from/,$query);
($this_page ne "") || ($this_page = 1);
$next_page = $this_page + 1;

print "Content-type: text/html

<head>
  <meta charset=utf-8>
	<title>Endloses CGI-Script</title>
</head>
<body>

<h1>Sie befinden sich auf Seite: $this_page !</h2>
";

print "
<a href=\"/cgi-bin/endless_cgi.cgi?${next_page}\">
 Nächste Seite</a>
";
