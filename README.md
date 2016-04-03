# NaturalDateFormat

A library to format dates as described in Material Design guidelines (https://www.google.com/design/spec/patterns/data-formats.html#data-formats-date-time).

RelativeDateFormat formats date as relative (4 months ago, today, next year), AbsoluteDateFormat formats date as absolute (Monday, 4:32 PM, June 10). Both use natural language

![Screenshot](https://github.com/ZieIony/NaturalDateFormat/blob/master/images/screenshot.png)

##### How to install

Follow instructions on https://jitpack.io/#ZieIony/NaturalDateFormat

##### How to use

    RelativeDateFormat relFormat = new RelativeDateFormat(context,NaturalDateFormat.DATE);
    AbsoluteDateFormat absFormat = new AbsoluteDateFormat(context,NaturalDateFormat.DATE | NaturalDateFormat.HOURS | NaturalDateFormat.MINUTES);
    relFormat.format(new Date().getTime());
    absFormat.format(new Date().getTime());

The second parameter can be a combination of flags from NaturalDateFormat class. See the sample app.

##### Translations

I know only english and polish. If you wish to help me with translations, feel free to make a pull request with a translation.

There are issues with certain languages on certain platforms. For example polish doesn't work well on Samsung with API 10. It's an issue with Android internals and there's not much I can do with that. 
