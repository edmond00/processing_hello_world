NAME= game
MAIN_CLASS= game.Game
PROCESSING= .:jar/core.jar:.:jar/minim.jar:.:jar/jsminim.jar:.:jar/mp3spi1.9.5.jar:.:jar/jl1.0.1.jar:.:jar/tritonus_share.jar:.:jar/tritonus_aos.jar
MANIFEST= MANIFEST.MF
JARNAME= launch.jar
JARDIR= jar
DATADIR= data
ZIP= game.zip
SRC_DIR= ./src/
rwildcard=$(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2) $(filter $(subst *,%,$2),$d))
SRC= $(call rwildcard, $(SRC_DIR), *.java)
SRC_NAME= $(SRC:$(SRC_DIR)%=%)
OBJ_DIR= ./class/
OBJ_NAME= $(SRC_NAME:.java=.class)
OBJ= $(addprefix $(OBJ_DIR), $(OBJ_NAME))

.PHONY: all
all: jar

$(OBJ):
	@printf "\033[2K[ \033[31mcompiling\033[0m ] $@ \n"
	@mkdir $(OBJ_DIR) 2> /dev/null || echo "" > /dev/null
	javac -d $(OBJ_DIR) -classpath $(PROCESSING) $(SRC)


.PHONY: clean
clean:
	@printf "[ \033[36mdelete class\033[0m ]\n"
	@rm -Rf $(OBJ_DIR)

.PHONY: fclean
fclean: clean
	@printf "[ \033[36mdelete jar and zip\033[0m ]\n"
	@rm -f $(JARNAME)
	@rm -f $(ZIP)

.PHONY: re
re: fclean all

.PHONY: rerun
rerun: fclean all run

.PHONY: jar
jar: $(JARNAME)

$(JARNAME): $(OBJ)
	@printf "[ \033[34mjar\033[0m ]\n"
	@jar cvmf MANIFEST.MF $(JARNAME) -C $(OBJ_DIR) .

.PHONY: run
run: jar
	@printf "[ \033[33mrun\033[0m ]\n"
	@java -jar $(JARNAME)

.PHONY: zip
zip: $(ZIP)

$(ZIP): $(JARNAME)
	@printf "[ \033[35mzip\033[0m ]\n"
	@zip -r $(ZIP) $(JARNAME) $(DATADIR) $(JARDIR)
