package com.doosan.heritage.service;

import com.doosan.heritage.constant.NewsTypeConstant;
import com.doosan.heritage.constant.YesNoConstant;
import com.doosan.heritage.model.News;
import com.doosan.heritage.repository.NewsRepository;
import com.doosan.heritage.repository.NewsThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class NewsService {

	private static final Sort newsDefaultSort = Sort.by("newsPressDate").descending();

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private NewsThumbnailRepository newsThumbnailRepository;

	public Page<News> getMainNewsByLanguage(String localeLanguage) {
		int page = 0;
		int pageSize = 3;
		Pageable pageable = PageRequest.of(page, pageSize, newsDefaultSort);

		return newsRepository.findByNewsLanguageAndNewsDisplayed(localeLanguage, YesNoConstant.YES, pageable);
	}

	public Page<News> getNewsPageNewsListByLanguageAndPageAndQuery(String localeLanguage, String query, int page) {
		int pageSize = 9;
		Pageable pageable = PageRequest.of(page, pageSize, newsDefaultSort);

		return newsRepository.findByNewsLanguageAndNewsDisplayedAndQuery(localeLanguage, YesNoConstant.YES, query, pageable);
	}

	public News getNewsPageNewsByLanguageAndNewsId(String localeLanguage, Long newsId) {
		News news = newsRepository.findById(newsId).orElse(new News());

		if (Objects.equals(news.getNewsLanguage(), localeLanguage) &&
				Objects.equals(news.getNewsType(), NewsTypeConstant.NEWS_TYPE_NEWS)
		) {
			return news;
		} else {
			return null;
		}
	}

	public News getPreviousNews(News news) {
		if (news == null) {
			return null;
		}

		String language = news.getNewsLanguage();
		Date pressDate = news.getNewsPressDate();

		return newsRepository.findFirstByNewsLanguageAndNewsDisplayedAndNewsTypeAndNewsPressDateGreaterThanOrderByNewsPressDateAsc(
				language, YesNoConstant.YES, NewsTypeConstant.NEWS_TYPE_NEWS, pressDate);
	}

	public News getNextNews(News news) {
		if (news == null) {
			return null;
		}

		String language = news.getNewsLanguage();
		Date pressDate = news.getNewsPressDate();

		return newsRepository.findFirstByNewsLanguageAndNewsDisplayedAndNewsTypeAndNewsPressDateLessThanOrderByNewsPressDateDesc(
				language, YesNoConstant.YES, NewsTypeConstant.NEWS_TYPE_NEWS, pressDate);
	}
}
