package com.example.app_didi_lanches.data.database.repository

import android.content.Context
import com.example.app_didi_lanches.data.database.AppDatabase
import com.example.app_didi_lanches.data.database.dao.UsuarioDao
import com.example.app_didi_lanches.data.database.entity.Usuario

class UsuarioRepository(context: Context) {

    private val  usuarioDao: UsuarioDao

    init {
        val db = AppDatabase.getDatabase(context)
        usuarioDao = db.usuarioDao()
    }

    suspend fun inserir(usuario: Usuario) = usuarioDao.inserir(usuario)

    suspend fun buscarPorId(id: Int) = usuarioDao.buscarPorId(id)

    suspend fun listarTodos() = usuarioDao.listarTodos()

    suspend fun atualizar(usuario: Usuario) = usuarioDao.atualizar(usuario)

    suspend fun deletar(usuario: Usuario) = usuarioDao.deletar(usuario)
}