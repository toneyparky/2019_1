
# coding: utf-8

# # 51840 개의 단어로 이루어진 단어 파일을 이분탐색과 해싱탐색으로 단어에 해당하는 뜻을 검색하는 프로그램 
# 
# 
# # 해시테이블 크기에 따라 찾은 결과 값
# 
# 52000
# apple 14, 5
# roi 15, 0
# longline 15, 0
# ran 8 ,1
# toothpaste  14, 17
# didymous 15, 26
# rhombus 14, 102
# 
# 
# 70000
# apple 14, 0
# roi 15, 0
# longline 15, 0
# ran 8 ,1
# toothpaste 14, 2
# didymous 15, 7
# rhombus 14, 12
# 
# 
# 80000
# ~
# 이미 충분히 작기에 생략 
# ~
# toothpaste 14,0 
# didymous 15, 6
# rhombus 14, 1
# 

# In[144]:


#51840 개의 단어로 이루어진 단어 파일을 이분탐색과 해싱탐색으로 단어에 해당하는 뜻을 검색하는 프로그램 
TABLESIZE = 52000 #70000, 80000의 경우도 해봤다.

HT = HashTable(TABLESIZE)
HT.file_read() #파일을 읽어서 단어와 뜻이 결합된 한 문자열을 리스트의 요소로 저장.
HT.word_set() #리스트의 요소들을 단어 따로 뜻 따로 리스트에 저장.

word = input("단어 : ")


print("")

#바이너리 서치
#바이너리 서치를 한다. (그에 해당하는 인덱스와 몇 번 바이너리 서치를 했는지를 각각 ind와 bin_count에 저장)
ind, bin_count = HT.bin_search(word)

print("binary search")
print(HT.sorted_dic[ind].replace(HT.sorted_dic[ind].split()[0], "").split(":")[1].strip(), "(", bin_count, ")")


#해시 서치
#해쉬 키를 받아서 해쉬에 넣어준다.
for i in range(len(HT.dictionary)):
    HT.hash_insert(HT.get_key_by_ind(i), i)

#해시 서치를 위한 해시값을 받아온다.
key2 = HT.get_key_by_word(word)

#해시 서치를 위한 해시 값을 이용하여 해시 서치를 한다. (그에 해당하는 뜻과 몇 번 충돌이 일어났는지를 각각 mean과 count에 저장)
mean, count = HT.hash_search(key2, word)

print("")
print("hash search")
print(mean, " (", count, ")")


# In[143]:


#해시함수와, 해시테이블, 충돌시 전략, 해시 탐색 그리고 이분 탐색 기능이 있는 클래스.

class HashTable():
    count = 0 #해시 탐색 충돌 횟수
    dictionary = [] #처음 파일을 읽어서 단어와 뜻을 저장
    words = [] #단어만 저장
    meanings = [] #뜻만 저장
    sorted_dic = [] #이분 탐색을 위한 정렬된 단어와 뜻을 저장할 리스트
    
    def __init__(self, size):
        self.hashTable = [[None]*2 for i in range(TABLESIZE)] #크기만큼 단어, 뜻을 담을 리스트
        self.dictionary = [] 
        
    def file_read(self):
        f = open("/Users/ToneyParky/Desktop/new_dict-utf8-1.txt", "r", encoding='utf-8-sig')
        #파일에서 한 줄씩 읽어오기
        while True:
            line = f.readline()
            if not line: break
            self.dictionary.append(line)
        f.close()
        self.dictionary[0] = self.dictionary[0].replace('\ufeff', '') #유니코드-8 사용시 발생하는 예외처리 
        

    def word_set(self):
        #한 줄에서 첫 단어(영단어)만 따로(words), 뜻만 따로(meanings) 저장하기
        for i in range(len(self.dictionary)):
            self.words.append(self.dictionary[i].split()[0])
            self.meanings.append(self.dictionary[i].replace(self.dictionary[i].split()[0], "").split(":")[1].strip())

    #단어 리스트에 들어있는 리스트로 해시 키 찾기. 
    def get_key_by_ind(self, ind):
        h = 0
        for j in range(len(self.words[i])):
            h = (31 * h + ord(self.words[i][j])) % TABLESIZE
        return h
    
    #사용자로부터 받은 입력값으로 해시 키 찾기.
    def get_key_by_word(self, word):
        h = 0
        for j in range(len(word)):
            h = (31 * h + ord(word[j])) % TABLESIZE
        return h
    
    #찾은 해시 키로 해시 테이블에 넣기 (충돌 전략도 포함) -> 충돌전략은 "하나 다음 인덱스로 간다." (for문을 돌기에)
    def hash_insert(self, key, ind):
        self.key = key
        
        #key부터 끝까지 돈다!
        for i in range(key, TABLESIZE): 
            if(self.hashTable[i][0] == None):
                self.hashTable[i][0] = self.words[ind]
                self.hashTable[i][1] = self.meanings[ind]
                return None
            else:
                continue
         
        #처음부터 key직전까지 돈다!
        for i in range(0, key):
            if(self.hashTable[i][0] == None):
                self.hashTable[i][0] = self.words[ind]
                self.hashTable[i][1] = self.meanings[ind]
                return None
            else:
                continue
        print("FULL") #오류발생시
        return None
        
        
    #해시 탐색 (충돌 전략도 포함) -> 충돌전략은 "하나 다음 인덱스로 간다." (count로 표시)
    def hash_search(self, key, word):
        count = 0
        #key부터 끝까지 돈다!
        for i in range(key, TABLESIZE):
            if(self.hashTable[i][0] == word):
                return self.hashTable[i][1], count
            else:
                count += 1
                continue
        
        #처음부터 key직전까지 돈다!
        for i in range(0, key):
            if(self.hashTable[i][0] == word):
                return self.hashTable[i][1], count
            else:
                count += 1
                continue
                
        #찾는 단어가 없을 시
        print("There is no word such like this : ", word)
        return word, count
    
    #이분 탐색
    def bin_search(self, word):
        self.sorted_dic = sorted(self.dictionary)
        start = 0
        end = len(self.dictionary) - 1
        bin_count = 0
        
        while start <= end:
            mid = (start + end) // 2 #몫을 내림하는 나누기
            if self.sorted_dic[mid].split()[0] == word:
                return mid, bin_count
            elif self.sorted_dic[mid].split()[0] < word:
                start = mid + 1
                bin_count += 1
            else:
                end = mid - 1
                bin_count += 1
        return mid, bin_count

