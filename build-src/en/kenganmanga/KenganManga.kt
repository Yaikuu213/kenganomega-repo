package eu.kanade.tachiyomi.extension.en.kenganmanga

import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.source.online.ParsedHttpSource
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.Calendar

class KenganManga : ParsedHttpSource() {

    override val name = "Kengan Manga Online"
    override val baseUrl = "https://w15.kengan-manga.com"
    override val lang = "en"
    override val supportsLatest = true

    // --- Liste des Mangas (Accueil/Populaire) ---
    override fun popularMangaSelector() = "div.post-item"
    override fun popularMangaNextPageSelector() = "a.next"
    
    override fun popularMangaFromElement(element: Element): SManga {
        val manga = SManga.create()
        manga.setUrlWithoutDomain(element.select("a").attr("href"))
        manga.title = element.select("h3").text()
        manga.thumbnail_url = element.select("img").attr("src")
        return manga
    }

    // --- Détails du Manga ---
    override fun mangaDetailsParse(document: Document): SManga {
        val manga = SManga.create()
        manga.author = "Sandrovich Yabako"
        manga.description = document.select("div.entry-content p").first()?.text()
        manga.genre = "Action, Martial Arts, Seinen"
        manga.status = SManga.ONGOING
        return manga
    }

    // --- Liste des Chapitres ---
    override fun chapterListSelector() = "ul.chapter-list li"
    
    override fun chapterFromElement(element: Element): SChapter {
        val chapter = SChapter.create()
        val link = element.select("a")
        chapter.setUrlWithoutDomain(link.attr("href"))
        chapter.name = link.text()
        chapter.date_upload = Calendar.getInstance().timeInMillis // Date actuelle par défaut
        return chapter
    }

    // --- Lecture des Pages (Images) ---
    override fun pageListParse(document: Document): List<eu.kanade.tachiyomi.source.model.Page> {
        return document.select("div.entry-content img").mapIndexed { i, img ->
            eu.kanade.tachiyomi.source.model.Page(i, "", img.attr("src"))
        }
    }

    // Méthodes requises par l'interface mais inutilisées ici
    override fun latestUpdatesSelector() = popularMangaSelector()
    override fun latestUpdatesFromElement(element: Element) = popularMangaFromElement(element)
    override fun latestUpdatesNextPageSelector() = popularMangaNextPageSelector()
    override fun searchMangaSelector() = popularMangaSelector()
    override fun searchMangaFromElement(element: Element) = popularMangaFromElement(element)
    override fun searchMangaNextPageSelector() = popularMangaNextPageSelector()
    override fun imageUrlParse(document: Document) = throw UnsupportedOperationException("Non utilisé")
}