package com.example.app_didi_lanches.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app_didi_lanches.data.database.entity.Usuario

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT * FROM usuarios")
    suspend fun listarTodos(): List<Usuario>

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun deletar(usuario: Usuario)
}