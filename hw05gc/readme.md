Измерение активности GC
Запуск
mvn clean package
./start.sh


Результаты:

Общее время работы приложения:
1. SerialGC -XX:+UseSerialGC      - 301,184s
2. G1 (GarbageFirst) -XX:+UseG1GC - 292,883s
3. ParallelGC -XX:+UseParallelGC  - 275,770s

Кол-во Young сборок в минуту:
1. G1 (GarbageFirst) -XX:+UseG1GC - 14
2. ParallelGC -XX:+UseParallelGC  - 7
3. SerialGC -XX:+UseSerialGC      - 5

Кол-во Old сборок в минуту:
1. ParallelGC -XX:+UseParallelGC  - 4
1. SerialGC -XX:+UseSerialGC      - 4
2. G1 (GarbageFirst) -XX:+UseG1GC - 2

Cреднее продолжение Young сборки(min):
1. ParallelGC -XX:+UseParallelGC  - 0.0017833369
2. G1 (GarbageFirst) -XX:+UseG1GC - 0.0031500063
3. SerialGC -XX:+UseSerialGC      - 0.0123666914

Cреднее продолжение Old сборки(min):
1. SerialGC -XX:+UseSerialGC      - 0.0019666706
2. G1 (GarbageFirst) -XX:+UseG1GC - 0.0039833413
3. ParallelGC -XX:+UseParallelGC  - 0.0066833467

Вывод: 
Был впечатлен G1. Самые частые Young сборки при средней скорости выполнения. Полагаю G1 покажет себя лучше в 
многопроцессорном приложении. 
При установленных настройках приложение продолжительнее работало с SerialGC.

До проведения исследования думал что G1 будет иметь сильное преимущество перед другими сборщиками.
Теперь понял что сборщик это инструмент, и нужно применять его по обстоятельствам.