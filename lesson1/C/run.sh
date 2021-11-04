#!/bin/bash


for ((var=6; var < 101; var++)); do
	pow=$((var*var))
	curl 'http://1d3p.wp.codeforces.com/new' \
  -H 'Connection: keep-alive' \
  -H 'Cache-Control: max-age=0' \
  -H 'Upgrade-Insecure-Requests: 1' \
  -H 'Origin: http://1d3p.wp.codeforces.com' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36' \
  -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9' \
  -H 'Referer: http://1d3p.wp.codeforces.com/' \
  -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
  -H 'Cookie: JSESSIONID=26E07AED86C8D7216D00359E85FBE645' \
  --data-raw "_af=34be50b38beccce4&proof=$pow&amount=$var&submit=Submit" \
  --compressed \
  --insecure
  
done

  
