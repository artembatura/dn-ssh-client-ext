<?php

namespace php\ssh\client;

/**
 * Class Connection
 * @package php\ssh\client
 */
abstract class Connection
{
	/**
     * description
     * @return Commander
     */
	public function execute() {
	}
	
	/**
     * Close active connection
	 * @return void
     */
	public function close() {
    }
	
	/**
     * description
	 * @return string
     */
    public function getHost() {
    }
	
	/**
     * description
	 * @return int
     */
    public function getPort() {
    }
}