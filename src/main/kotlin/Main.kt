import kotlinx.coroutines.delay
import me.tomasan7.jecnaapi.JecnaClient
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

suspend fun main(args: Array<String>)
{
    if (!Desktop.isDesktopSupported())
    {
        println("Desktop API is not supported on your system.")
        return
    }

    val username = args.getOrNull(0)
    val password = args.getOrNull(1)

    if (username == null || password == null)
    {
        println("Please provide username and password as arguments.")
        return
    }

    val jecnaClient = JecnaClient()
    val isSuccess = jecnaClient.login(username, password)

    if (!isSuccess)
    {
        println("Incorrect username or password.")
        return
    }

    val timetable = jecnaClient.getTimetablePage().timetable

    for (dayOfWeek in DayOfWeek.values())
    {
        for (lessonPeriod in timetable.lessonPeriods)
        {
            val lessonSpot = timetable.getLessonSpot(dayOfWeek, lessonPeriod) ?: continue

            if (lessonSpot.isEmpty())
                continue

            val next = TemporalAdjusters.next(dayOfWeek)
            val fromDateTime = LocalDateTime.now().with(next).withHour(lessonPeriod.from.hour).withMinute(lessonPeriod.from.minute)
            val toDateTime = LocalDateTime.now().with(next).withHour(lessonPeriod.to.hour).withMinute(lessonPeriod.to.minute)

            val fromFormatted = FORMAT.format(fromDateTime)
            val toFormatted = FORMAT.format(toDateTime)

            val arguments = mapOf(
                "action" to "TEMPLATE",
                "text" to lessonSpot.lessons.first().subjectName.full,
                "dates" to "$fromFormatted/$toFormatted"
            )

            val encodedArguments = arguments.mapValues {
                URLEncoder.encode(it.value, "UTF-8")
            }

            val encodedUrlArgsStr = encodedArguments.toUrlArgs()

            Desktop.getDesktop().browse(URI("https://calendar.google.com/calendar/render?$encodedUrlArgsStr"))

            delay(500)
        }
    }
}

val FORMAT = DateTimeFormatter.ofPattern("YYYYMMdd'T'HHmmss")

private fun Map<String, String>.toUrlArgs() = this.entries.joinToString("&") { "${it.key}=${it.value}" }
