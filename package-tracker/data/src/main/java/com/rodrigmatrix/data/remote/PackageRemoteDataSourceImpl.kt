package com.rodrigmatrix.data.remote

import com.rodrigmatrix.data.model.response.CorreiosPackageResponse
import com.rodrigmatrix.data.model.response.Evento
import com.rodrigmatrix.data.model.response.Objeto
import com.rodrigmatrix.data.model.response.TipoPostal
import com.rodrigmatrix.data.service.CorreiosService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PackageRemoteDataSourceImpl(
    private val correiosService: CorreiosService,
) : PackageRemoteDataSource {

    override fun getPackage(packageId: String): Flow<CorreiosPackageResponse> =
        flow {
            val response = correiosService.getPackage(packageId)
            if (response.objetos?.firstOrNull()?.mensagem?.contains("Objeto não encontrado") == true) {
                emit(CorreiosPackageResponse(objetos = listOf(Objeto(codObjeto = packageId))))
            } else {
                emit(response)
            }
        }
}