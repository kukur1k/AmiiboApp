package com.example.amiiboapp.domain.usecase

import com.example.amiiboapp.domain.model.Note
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.jetbrains.annotations.TestOnly
import org.junit.Test

class ValidateNoteUseCaseTest {

    private val fakeRepository = FakeNoteRepository()

    private val useCase = ValidateNoteUseCase()

    private fun note(id: String, amiiboId: String, text: String, ratingNote: Int, imgPath: String) =
        Note(id, amiiboId, text, ratingNote, imgPath)

    @Test
    fun ValidateCountStarsOver() = runTest {
        fakeRepository.setNotes(listOf(note("1", "1", "New Test Note 1", 6, "test_image_path.jpg")))
        val newNote = fakeRepository.getAllNotes().first()
        val result = useCase(newNote)
        assertEquals(ValidationResult(false, NoteCreateErrors(starsCount = "укажите рейтинг от 1 до 5")), result)
    }

    @Test
    fun ValidateCountStarsLess() = runTest {
        fakeRepository.setNotes(listOf(note("1", "1", "New Test Note 1", -1, "test_image_path.jpg")))
        val newNote = fakeRepository.getAllNotes().first()
        val result = useCase(newNote)
        assertEquals(ValidationResult(false, NoteCreateErrors(starsCount = "укажите рейтинг от 1 до 5")), result)
    }

    @Test
    fun ValidateCountStarsNormal() = runTest {
        fakeRepository.setNotes(listOf(note("1", "1", "New Test Note 1", 4, "test_image_path.jpg")))
        val newNote = fakeRepository.getAllNotes().first()
        val result = useCase(newNote)
        assertEquals(ValidationResult(true, NoteCreateErrors()), result)
    }

    @Test
    fun ValidateEmptyText() = runTest {
        fakeRepository.setNotes(listOf(note("1", "1", "", 4, "test_image_path.jpg")))
        val newNote = fakeRepository.getAllNotes().first()
        val result = useCase(newNote)
        assertEquals(ValidationResult(false, NoteCreateErrors(emptyText = "Текст не должен быть пустым")), result)
    }

}