/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package com.alan.clients.utility.notebot.decoder;

import com.alan.clients.module.impl.fun.NoteBot;
import com.alan.clients.utility.notebot.song.Song;

import java.io.File;

public abstract class SongDecoder {
    protected NoteBot notebot = (NoteBot) NoteBot.getInstance();

    /**
     * Parse file to a {@link Song} object
     *
     * @param file Song file
     * @return A {@link Song} object
     */
    public abstract Song parse(File file) throws Exception;
}
