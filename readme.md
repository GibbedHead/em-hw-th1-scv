# CSV генератор

## Задача

    Разработать модуль для генерации CSV файлов из объектов Java

## Проверка

### Тесты основного модуля:

```bash
git clone https://github.com/GibbedHead/em-hw-th1-csv.git;
cd em-hw-th1-csv/csvwriter;
mvn test;
```

### Тестовый проект, использующий основной модуль

```bash
cd em-hw-th1-csv/csvwriter;
mvn clean install;
cd ../csvwriterrunner;
mvn clean package;
java -jar target/csvwriterrunner-1.0-SNAPSHOT.jar;
```

## Детали решения

С помощью аннотации [CSVField.java](csvwriter%2Fsrc%2Fmain%2Fjava%2Fru%2Fchaplyginma%2Fcsvwriter%2Fannotation%2FCSVField.java) можно пометить поля объектов, которые мы хотим сохранять в csv файл. Для аннотации есть обязательный параметр `name`, который содержит заголовок для столбца в csv файле.

Проект поддерживает запись в csv следующих типов данных:
* примитивы
* обертки примитивов
* строки
* массивы вышеуказанных типов(значения в файле будут разделены спец сепаратором)
* коллекции вышеуказанных типов(значения в файле будут разделены спец сепаратором)

Для настройки формата csv файла используется класс [CSVSchema.java](csvwriter%2Fsrc%2Fmain%2Fjava%2Fru%2Fchaplyginma%2Fcsvwriter%2Fschema%2FCSVSchema.java). При его создании надо указать для какого класса она создается и опционально передать параметры типа разделителя колонок, разделителя элементов массивов, экранирующего символа и символа, которыми будут заменяться `null` поля.

В проекте 2 папки:
- [csvwriter](csvwriter) - отдельный maven проект, который можно собрать в jar файл и использовать как зависимость
- [csvwriterrunner](csvwriterrunner) - простой проект с тестовым использованием первого проекта. Пишет при запуске в [people.csv](csv%2Fpeople.csv) тестовую коллекцию объектов класса [Person.java](csvwriterrunner%2Fsrc%2Fmain%2Fjava%2Fru%2Fchaplyginma%2Fdomain%2FPerson.java)




## Полный тест задачи
```
Мы пишем приложение, которое должно генерировать отчеты в разных форматах и нам хочется
генерировать какой-нибудь отчёт в CSV, но проблема в том, что у нас нет такого инструмента.
Поэтому мы решаем, что нам нужна библиотека для генерации CSV файлов.

Задача:
- Разработать модуль для генерации CSV файлов из объектов Java
- Для реализации можно использовать java reflection и annotations

Примерный API нашего модуля:

Map<Sting, Object> maybeSomeSettings = new HashMap<>();
CSVWriter writer = new CSVWriter(maybeSomeSettings);
List<BusinessObject> list = new ArrayList<>();
.
.
.
writer.writeToFile(list, "path/to/file.csv");

Это лишь пример API, можно сделать свою реализацию. Главная задача в том,
чтобы мы могли передать набор объектов с какими-то данными на вход и получить
на выходе csv файл. Код должен быть покрыт тестами и иметь main метод.

Дополнительные задания:
- Реализовать библиотеку как maven артефакт, чтобы можно было подключать к другим проектам.
```