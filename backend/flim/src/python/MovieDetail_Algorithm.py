import sys
import re
from konlpy.tag import Okt
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

# 표준 출력을 UTF-8로 설정
sys.stdout.reconfigure(encoding='utf-8')


# 한글 불용어 리스트 (기본적인 예시)
KOREAN_STOPWORDS = ['이', '그', '저', '것', '등', '도', '은', '는', '이랑', '랑', '들', '가', '을', '를', '에', '에서', '와', '과']

# 형태소 분석기를 위한 함수 (Okt 사용)
def preprocess_korean_text(text):
    okt = Okt()
    # 텍스트에서 한글과 공백만 남기기
    text = re.sub(r'[^ㄱ-ㅎㅏ-ㅣ가-힣\s]', '', text)
    
    # 형태소 분석을 통해 명사만 추출 (불용어 제외)
    words = okt.nouns(text)
    filtered_words = [word for word in words if word not in KOREAN_STOPWORDS and len(word) > 1]  # 길이가 1인 단어 제외

    return ' '.join(filtered_words)


def analyze_text(user_movie_description, movie_descriptions):
    
    # 사용자 영화 설명과 다른 영화 설명을 합침
    all_movie_descriptions = [user_movie_description] + movie_descriptions

     # 한글 텍스트 전처리: 사용자 영화와 다른 영화 설명을 형태소 분석 후 전처리
    processed_descriptions = [preprocess_korean_text(description) for description in all_movie_descriptions]
    
    # TF-IDF 벡터라이저
    tfidf_vectorizer = TfidfVectorizer()

    # 영화 설명들을 벡터화
    tfidf_matrix = tfidf_vectorizer.fit_transform(processed_descriptions)

    #디버깅 : print("TF-IDF matrix shape:", tfidf_matrix.shape)  # 디버깅: TF-IDF 행렬의 크기 출력
    # 사용자 영화와 다른 영화들 간의 유사도 계산 (개별적으로 계산)
    cosine_similarities = cosine_similarity(tfidf_matrix[0:1], tfidf_matrix[1:])
    cosine_similarities = np.clip(cosine_similarities, 0, 1)
    return cosine_similarities[0]

def read_file(file_path):
    """파일 경로를 받아서 해당 파일의 내용을 읽고 구분자를 기준으로 나누는 함수"""
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read().strip()
            # 구분자를 기준으로 텍스트를 나누기 (구분자는 "==="으로 설정)
            movie_overviews = content.split("===")  # "==="을 구분자로 설정
            movie_overviews = [overview.strip() for overview in movie_overviews if overview.strip()]
            return movie_overviews
    except FileNotFoundError:
        print(f"Error: The file {file_path} does not exist.")
        return []
    except Exception as e:
        print(f"Error reading file {file_path}: {e}")
        return []

def process_movie_data(movie_data):
    """
    영화 제목과 줄거리를 합쳐 하나의 문자열로 만들어서 유사도 분석에 사용할 수 있도록 전처리
    """
    processed_data = []
    for movie in movie_data:
        try:
            # movie는 title, overview로 구성 (genre_ids 제외)
            title = movie.get("title", "").strip()
            description = movie.get("overview", "").strip()
            combined_text = f"Title: {title} Description: {description}"
            processed_data.append(combined_text)
        except Exception as e:
            print(f"Error processing movie: {movie} - {e}")
    return processed_data

if __name__ == "__main__":
    # 명령줄 인자로 받은 데이터를 처리
    if len(sys.argv) != 3:
    #디버깅 : print("Usage: python script.py <user_movie_overview_file> <movie_overviews_file>")
        sys.exit(1)

    user_movie_overview_file = sys.argv[1]  # 사용자 영화 설명 파일 경로
    movie_overviews_file = sys.argv[2]  # 다른 영화들의 설명 파일 경로

    # 파일에서 실제 텍스트 내용을 읽어옴
    user_movie_overview_list = read_file(user_movie_overview_file)
    if not user_movie_overview_list:
        print("No user movie overview found. Exiting.")
        sys.exit(1)

    #선택한 영화의 줄거리 (디버깅용)
    #디버깅: print("User movie overview content:")
    #디버깅: for line in user_movie_overview_list:
    #디버깅: print(line)  # 여기서 파일 내용이 제대로 출력되는지 확인

    user_movie_overview = user_movie_overview_list[0]  # 첫 번째 영화 줄거리

    movie_overviews = read_file(movie_overviews_file)

    if not movie_overviews:
        print("No movie overviews found. Exiting.")
        sys.exit(1)

    # 각 영화의 title, overview 형식으로 데이터를 준비 (genre_ids 제외)
    movie_data = []
    for movie_overview in movie_overviews:
        # 각 영화 설명을 구분자로 나누기
        movie_info = movie_overview.split("\n")
        if len(movie_info) >= 2:  # "title"과 "overview"만 존재
            title = movie_info[0].replace("title:", "").strip()
            overview = movie_info[1].replace("overview:", "").strip()

            movie_data.append({"title": title, "overview": overview})

    # 사용자 영화에 대한 title, overview 추출
    user_movie_info = user_movie_overview.split("\n")
    if len(user_movie_info) >= 2:  # "title"과 "overview"만 존재
        user_title = user_movie_info[0].replace("title:", "").strip()
        user_overview = user_movie_info[1].replace("overview:", "").strip()

        user_movie_data = {"title": user_title, "overview": user_overview}
    else:
        print("Error: Invalid user movie overview format.")
        sys.exit(1)

    # 영화 데이터를 전처리하여 분석에 사용할 텍스트 준비
    processed_movies = process_movie_data(movie_data)
    processed_user_movie = process_movie_data([user_movie_data])[0]

    # 데이터가 제대로 처리되었는지 확인
    #디버깅 : print("선택한 영화 줄거리 :", processed_user_movie)
    #디버깅 : for pm in processed_movies:
    #디버깅 : print(pm)

    if not processed_user_movie or not processed_movies:
        print("Error: No valid movie descriptions for similarity calculation.")
        sys.exit(1)

    # 유사도 계산
    similarities = analyze_text(processed_user_movie, processed_movies)

    
    #유사도 출력 (유사도 비교 디버깅 출력)
    #코드: for i, similarity in enumerate(similarities):
    #코드: print(f"Similarity with Movie {i + 1}: {similarity:.4f}")

    # 유사도 내림차순으로 인덱스 추출
    top_5_indices = np.argsort(similarities)[::-1][:10]

    #추천 영화 출력
    for i, idx in enumerate(top_5_indices):
        print(f"Rank {i+1}: Movie {idx + 1}, Similarity: {similarities[idx]:.4f}")
        print(f"data:\n{movie_overviews[idx]}\n")
