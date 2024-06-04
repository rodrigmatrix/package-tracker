package com.rodrigmatrix.packagetracker.extensions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.rodrigmatrix.data.model.PackageLastStatus
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.packagetracker.presentation.theme.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import kotlin.math.absoluteValue

@Composable
fun UserPackage.getLastStatus(): PackageLastStatus {
    val lastStatus = statusUpdateList.firstOrNull() ?: return PackageLastStatus(
        title = "Encomenda não encontrada na base de dados",
        description = "",
        color = md_theme_light_primary,
        icon = Icons.Outlined.Done,
    )

    val (color, vector) = lastStatus.getStatusIconAndColor()

    return PackageLastStatus(
        title = lastStatus.title,
        description = lastStatus.description,
        color = color,
        icon = vector,
    )
}

@Composable
fun StatusUpdate?.getStatusIconAndColor(): Pair<Color, ImageVector> {
    val statusDescription = this?.title.orEmpty().lowercase()
    return when {
        this == null ->
            Pair(md_theme_light_error, Icons.Filled.Info)

        statusDescription.contains("encomenda cadastrada") ->
            Pair(theme_light_inProgress, Icons.Filled.FindInPage)

        statusDescription.contains("será entregue em instantes") ->
            Pair(theme_light_inRoute, Icons.Filled.LocalShipping)

        statusDescription.contains("saiu para entrega") ->
            Pair(theme_light_inRoute, Icons.Filled.LocalShipping)

        statusDescription.contains("não entregue") ->
            Pair(error, Icons.Filled.Close)

        statusDescription.contains("entregue") ->
            Pair(theme_light_done, Icons.Filled.Done)

        statusDescription.contains("objeto em transf") ->
            Pair(theme_light_inProgress, Icons.Filled.Cached)

        statusDescription.contains("encaminhado") ->
            Pair(theme_light_inProgress, Icons.Filled.Cached)

        statusDescription.contains("postado") ->
            Pair(theme_light_shipped, Icons.Filled.FlightTakeoff)

        statusDescription.contains("análise da autoridade aduaneira") ->
            Pair(theme_light_shipped, Icons.Filled.RequestQuote)

        statusDescription.contains("aguardando pagamento") ->
            Pair(MaterialTheme.colorScheme.error, Icons.Filled.RequestQuote)

        statusDescription.contains("pagamento confirmado") ->
            Pair(theme_light_customs, Icons.Filled.PriceCheck)

        statusDescription.contains("importação autorizada") ->
            Pair(theme_light_customs, Icons.Filled.AssignmentTurnedIn)

        statusDescription.contains("fiscalização aduaneira finalizada") ->
            Pair(theme_light_done_variant, Icons.Filled.Info)

        statusDescription.contains("objeto recebido pelos correios do brasil") ->
            Pair(theme_light_shipped, Icons.Filled.FlightLand)

        statusDescription.contains("objeto recebido na unidade de exportação do país de origem") ->
            Pair(theme_light_shipped, Icons.Filled.Flight)

        else -> Pair(theme_light_alert, Icons.Filled.Info)
    }
}

fun UserPackage.getCurrentStatusString(): String {
    val title = statusUpdateList.firstOrNull()?.title ?: return "Cadastrada"
    return when {
        title.contains("cadastrada") -> "Cadastrada"
        title.contains("entregue ao remetente") -> "Devolvido"
        title.contains("entregue ao destinatário") -> "Entregue"
        title.contains("roubado") -> "Roubado"
        title.contains("saiu para entrega") -> "Saiu para entrega"
        title.contains("em transferência") -> "A caminho"
        title.contains("Declaração aduaneira ausente ou incorreta") -> "Em devolução"
        title.contains("Saída do Centro Internacional") -> "Liberada da alfândega"
        title.contains("Pagamento confirmado") -> "Pagamento confirmado"
        title.contains("aguardando pagamento") -> "Aguardando pagamento"
        title.contains("importação autorizada") -> "Importação autorizada"
        title.contains("aguardando retirada") -> "Aguardando retirada"
        title.contains("prazo de retirada encerrado") -> "Não entregue"
        title.contains("A entrega não pode ser efetuada") -> "Não foi entregue"
        title.contains("de revisão do tributo") -> "Em revisão de tributos"
        title.contains("Revisão de tributos solicitada") -> "Em revisão de tributos"
        title.contains("Informações eletrônicas enviadas para análise da autoridade") -> "Em análise tributária"
        title.contains("recebido pelos Correios do Brasil") -> "Chegou ao Brasil"
        title.contains("objeto selecionado para conferência pela au") -> "Em fiscalização"
        title.contains("de apresentar documentação ou complementar") -> "Aguardando envio de documentação"
        title.contains("postado") -> "Postado"
        else -> title
    }
}

fun UserPackage.getDaysString(): String? {
    return try {
        val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
        val firstStatus = statusUpdateList.sortedBy {
            LocalDate.parse(it.date, formatter)
        }.firstOrNull()
        val firstDate = formatter.parseDateTime(firstStatus?.date)
        val lastDate = if (status.delivered) {
            formatter.parseDateTime(
                statusUpdateList.sortedByDescending {
                    LocalDate.parse(it.date, formatter)
                }.firstOrNull()?.date
            )
        } else {
            DateTime.now()
        }
        val days = Days.daysBetween(firstDate, lastDate).days.absoluteValue
        if (status.delivered) {
            "Entregue em $days dias"
        } else {
            "$days dias"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun UserPackage.getDeliveryDateString(): String {
    val status = statusUpdateList.firstOrNull()

    return try {
        val formatter = DateTimeFormat.forPattern("dd/MM/yyyy")
        val statusDate = formatter.parseDateTime(status?.date)
        val days = Days.daysBetween(statusDate, DateTime.now()).days.absoluteValue
        when (days) {
            0 -> "Entregue hoje"
            1 -> "Entregue ontem"
            else -> "Entregue em ${formatter.print(statusDate)}"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

