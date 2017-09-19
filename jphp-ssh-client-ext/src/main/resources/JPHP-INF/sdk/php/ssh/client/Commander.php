<?php

namespace php\ssh\client;

/**
 * Class Commander
 * @package php\ssh\client
 */
abstract class Commander
{
    /**
     * description
     * @param string $command
     * @param string $consoleCharset
     * @return void
     */
    public function exec($command, $consoleCharset) {
    }

    /**
     * description
     * @param string $command
     * @param string $password
     * @param string $consoleCharset
     * @return void
     */
    public function execWithSudo($command, $password, $consoleCharset) {
    }

	/**
     * Return last output from stderr
     * null - default java charset
     * @param string $toCharset
     * @return string
     */
    public function getLastError($toCharset = null) {
    }

	/**
     * Return last output from stdout
     * null - default java charset
     * @param string $toCharset
     * @return string
     */
    public function getLastOutput($toCharset = null) {
    }

    /**
     * Close active connection
     * @return void
     */
    public function end() {
    }
}