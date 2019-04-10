package com.arctouch.codechallenge.extensions

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.util.*

class LocaleExtensionsKtTest {

    private val locale = mockk<Locale>(relaxed = true)

    @Test
    fun mustReturnFormattedLanguage() {
        mockLocale()

        Assert.assertEquals("pt-BR", locale.getLanguageApiFormat())
    }

    private fun mockLocale() {
        every { locale.toString() }.returns("pt_BR")
        every { locale.country }.returns("BR")
    }
}